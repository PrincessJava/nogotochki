package ru.riton.ru.riton.handlers

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.CallbackQuery
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.bots.AbsSender
import ru.riton.ru.riton.createMessage
import ru.riton.ru.riton.model.enums.HandlerName
import ru.riton.ru.riton.editLastMessage
import ru.riton.ru.riton.service.AppointmentService
import ru.riton.ru.riton.service.ScheduleService
import ru.riton.ru.riton.service.UserService
import ru.riton.ru.riton.slotDate

@Component
class AppointmentHandler(
    private val appointmentService: AppointmentService,
    private val scheduleService: ScheduleService,
    private val userService: UserService) : CallbackHandler {
    override val name = HandlerName.APPOINTMENT

    override fun processCallbackData(absSender: AbsSender, callbackQuery: CallbackQuery, arguments: List<String>) {
        val schedule = scheduleService.getById(arguments.getOrNull(0)?.toIntOrNull())
        val tgId = callbackQuery.from.id
        val user = userService.getByTgId(tgId)

        appointmentService.checkUserAppointments(userId = user.id, slotId = schedule.id, start = schedule.start, finish = schedule.finish)
        appointmentService.addAppointment(userId = user.id, slotId = schedule.id)

        absSender.execute(createMessage((callbackQuery.message as Message).chat.id.toString(), "Вы успешно записаны к ${schedule.master!!.name} " +
                slotDate(schedule) +
                " на ${arguments.getOrNull(1)}"))
        editLastMessage(absSender, callbackQuery)
    }
}