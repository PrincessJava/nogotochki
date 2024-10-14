package ru.riton.exception

import java.lang.RuntimeException

class NoDataFoundException(name: String, val devMessage: String) : RuntimeException("Запись не найдена: $name")