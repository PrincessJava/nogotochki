package ru.riton.ru.riton.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.util.*

@Service
@PropertySource("classpath:titles/common.properties")
@PropertySource("classpath:titles/\${app.company.name}.properties")
class TitleService(
    @Value("\${text.no_free_slots:Нет свободных слотов}") private val noFreeSlots: String,
    @Value("\${button.previous_week:Пред. неделя}") private val prevWeek: String,
    @Value("\${button.next_week:След. неделя}") private val nextWeek: String,
    @Value("\${text.choose_day:Выберите удобный день}") private val chooseDay: String,
    @Value("\${text.choose_class:Выберите занятие}") private val chooseClass: String,
    @Value("\${text.choose_master:Выберите мастера}") private val chooseMaster: String,
    @Value("\${text.choose_time:Выберите удобное время}") private val chooseTime: String,
    @Value("\${text.not_registered:Вы не зарегистрированы. Пожалуйста, нажмите \"Зарегистрироваться\"}") private val notRegistered: String,
    @Value("\${button.register:Зарегистрироваться}") private val register: String,
    @Value("\${text.choose_assign:Как вам удобнее записаться?}") private val chooseAssign: String,
    @Value("\${button.class:На занятие}") private val onClass: String,
    @Value("\${button.master:К мастеру}") private val toMaster: String,
    @Value("\${text.already_registered:Вы уже зарегистрированы}") private val alreadyRegistered: String,
    @Value("\${text.write_name:Введите ваше имя:}") private val writeName: String,
    @Value("\${text.greeting:Привет, %s!}") private val greeting: String,
    @Value("\${button.buy:Купить абонемент/занятие}") private val buy: String,
    @Value("\${button.assign:Записаться}") private val assign: String,
    @Value("\${button.info:Информация}") private val info: String,
    @Value("\${button.admin:Связь с администратором}") private val admin: String,
    @Value("\${text.assign:Вы успешно записаны к %s %s в %s на %s}") private val assignSuccess: String,
    @Value("\${text.enter_phone:Введите ваш номер телефона в формате 8XXXXXXXXXX:}") private val enterPhone: String,
    @Value("\${text.enter_wrong_phone:Пожалуйста, введите номер телефона в формате 8XXXXXXXXXX:}") private val enterWrongPhone: String,
    @Value("\${text.registered:Спасибо, вы зарегистрированы!}") private val registered: String,


    ) {

    fun getGreeting(): String {
        return String(greeting.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }

    fun getNoFreeSlots(): String {
        return String(noFreeSlots.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getPrevWeek(): String {
        return String(prevWeek.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getNextWeek(): String {
        return String(nextWeek.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getChooseDay(): String {
        return String(chooseDay.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getChooseClass(): String {
        return String(chooseClass.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }

    fun getChooseMaster(): String {
        return String(chooseMaster.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getChooseTime(): String {
        return String(chooseTime.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getNotRegistered(): String {
        return String(notRegistered.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getRegister(): String {
        return String(register.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getChooseAssign(): String {
        return String(chooseAssign.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getOnClass(): String {
        return String(onClass.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getToMaster(): String {
        return String(toMaster.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getAlreadyRegistered(): String {
        return String(alreadyRegistered.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getWriteName(): String {
        return String(writeName.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getBuy(): String {
        return String(buy.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getAssign(): String {
        return String(assign.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getInfo(): String {
        return String(info.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getAdmin(): String {
        return String(admin.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getAssignSuccess(): String {
        return String(assignSuccess.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getEnterPhone(): String {
        return String(enterPhone.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getEnterWrongPhone(): String {
        return String(enterWrongPhone.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }
    fun getRegistered(): String {
        return String(registered.toByteArray(charset("ISO-8859-1")), charset("UTF-8"))
    }

}