package ru.riton.ru.riton.handlers

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessage
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.editLastMessage
import ru.riton.ru.riton.model.enums.ArgumentCode
import ru.riton.ru.riton.service.AppointmentService
import ru.riton.ru.riton.service.ScheduleService
import ru.riton.ru.riton.service.TitleService
import ru.riton.ru.riton.service.UserService
import ru.riton.ru.riton.slotDate
import ru.riton.ru.riton.slotTime

@Component
class AppointmentHandler(
    private val appointmentService: AppointmentService,
    private val scheduleService: ScheduleService,
    private val userService: UserService,
    private val titleService: TitleService
) : CallbackHandler {
    override val name = HandlerName.APPOINTMENT

    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: Map<ArgumentCode, String>) {
        val schedule = scheduleService.getById(arguments[ArgumentCode.SCHEDULE_ID]?.toIntOrNull())
        val tgId = callbackQuery.from.id
        val user = userService.getByTgId(tgId)

        appointmentService.checkUserAppointments(userId = user.id, slotId = schedule.id, start = schedule.start, finish = schedule.finish)
        appointmentService.addAppointment(userId = user.id, slotId = schedule.id)

        absSender.execute(
            createMessage(
                (callbackQuery.message as Message).chat.id.toString(), String.format(
                    titleService.getAssignSuccess(), schedule.master!!.name,
                    slotDate(schedule), slotTime(schedule), schedule.description!!.value
                )
            )
        )
//        editLastMessage(absSender, callbackQuery)
    }
}