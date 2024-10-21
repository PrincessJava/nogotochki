package ru.riton.ru.riton.config

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.AfterThrowing
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.CodeSignature
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*


@Aspect
@Component
class LogEntryExitAspect {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @Around("applicationPackagePointcut()")
    fun log(point: ProceedingJoinPoint): Any? {
        val codeSignature = point.signature as CodeSignature
        val methodSignature = point.signature as MethodSignature

        val method = methodSignature.method
        val methodName = method.name
        val className = method.declaringClass.name
        val unit = ChronoUnit.MILLIS

        val methodArgs = point.args
        val methodParams = codeSignature.parameterNames
        logger.info(entry(className, methodName, methodParams, methodArgs))

        val start = Instant.now()
        val response = point.proceed()
        val end = Instant.now()
        val duration = String.format(
            "%s %s",
            unit.between(start, end),
            unit.name
        )
        logger.info(exit(className, methodName, duration, response))
        return response
    }

    @Pointcut(
        "within(ru.riton.ru.riton.repository.*)" +
                " || within(ru.riton.ru.riton.service.*)" +
                " || within(ru.riton.ru.riton.controller.*)"
    )
    fun applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @AfterThrowing(
        pointcut = "applicationPackagePointcut()",
        throwing = "e"
    )
    fun logAfterThrowing(joinPoint: JoinPoint, e: Throwable) {
        val message = StringJoiner(" ")
            .add("Exception in")
            .add("${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}()")
            .add("with cause:")
            .add((e.cause ?: "NULL").toString())
            .toString()

        logger.error(message)
    }

    fun entry(className: String, methodName: String, params: Array<String>?, args: Array<Any>?): String {
        val message = StringJoiner(" ")
            .add("Started")
            .add("${className}.${methodName}()")

        if (Objects.nonNull(params) && Objects.nonNull(args) && params!!.size == args!!.size) {
            val values: MutableMap<String, Any> = HashMap(params.size)
            for (i in params.indices) {
                values[params[i]] = args[i]
            }
            message.add("with args:")
                .add(values.toString())
        }
        return message.toString()
    }

    fun exit(className: String, methodName: String?, duration: String?, result: Any?): String {
        val message = StringJoiner(" ")
            .add("Finished")
            .add("${className}.${methodName}()")

        message.add("in").add(duration)
        message.add("with return:").add(result.toString())

        return message.toString()
    }
}
