package ru.riton.ru.riton.model.enums

import java.time.DayOfWeek

enum class WeekDay(val rus: String) {
    MON("пон"),
    TUE("вт"),
    WED("ср"),
    THU("чт"),
    FRI("пт"),
    SAT("сб"),
    SUN("вс");

    companion object {
        fun toMap(): Map<WeekDay, String> {
            return entries.associateWith { it.rus }
        }

        fun fromDayOfWeek(dayOfWeek: DayOfWeek): WeekDay {
            return when (dayOfWeek) {
                DayOfWeek.MONDAY -> MON
                DayOfWeek.TUESDAY -> TUE
                DayOfWeek.WEDNESDAY -> WED
                DayOfWeek.THURSDAY -> THU
                DayOfWeek.FRIDAY -> FRI
                DayOfWeek.SATURDAY -> SAT
                DayOfWeek.SUNDAY -> SUN
            }
        }
    }
}