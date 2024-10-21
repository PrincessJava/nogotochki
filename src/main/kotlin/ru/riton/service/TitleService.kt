package ru.riton.ru.riton.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:titles/common.properties")
@PropertySource("classpath:titles/\${app.company.name}.properties")
class TitleService(
    @Value("\${text.no_free_slots:Нет свободных слотов}") val noFreeSlots: String,
    @Value("\${button.previous_week:Пред. неделя}") val prevWeek: String,
    @Value("\${button.next_week:След. неделя}") val nextWeek: String,
    @Value("\${text.choose_day:Выберите удобный день}") val chooseDay: String,
    @Value("\${text.choose_class:Выберите занятие}") val chooseClass: String,
    @Value("\${text.choose_time:Выберите удобное время}") val chooseTime: String,
    @Value("\${text.not_registered:Вы не зарегистрированы. Пожалуйста, нажмите \"Зарегистрироваться\"}") val notRegistered: String,
    @Value("\${button.register:Зарегистрироваться}") val register: String,
    @Value("\${text.choose_assign:Как вам удобнее записаться?}") val chooseAssign: String,
    @Value("\${button.class:На занятие}") val onClass: String,
    @Value("\${button.master:К мастеру}") val toMaster: String,
    @Value("\${text.already_registered:Вы уже зарегистрированы}") val alreadyRegistered: String,
    @Value("\${text.write_name:Введите ваше имя:}") val writeName: String,
    @Value("\${text.greeting:Привет, %s!}") val greeting: String,
    @Value("\${button.buy:Купить абонемент/занятие}") val buy: String,
    @Value("\${button.assign:Записаться}") val assign: String,
    @Value("\${button.info:Информация}") val info: String,
    @Value("\${button.admin:Связь с администратором}") val admin: String,
    @Value("\${text.assign:Вы успешно записаны к %s %s в %s на %s}") val assignSuccess: String,
    @Value("\${text.enter_phone:Введите ваш номер телефона в формате 8XXXXXXXXXX:}") val enterPhone: String,
    @Value("\${text.enter_wrong_phone:Пожалуйста, введите номер телефона в формате 8XXXXXXXXXX:}") val enterWrongPhone: String,
    @Value("\${text.registered:Спасибо, вы зарегистрированы!}") val registered: String,


    ) {
}