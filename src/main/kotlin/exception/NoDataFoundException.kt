package ru.riton.exception

import java.lang.RuntimeException

class NoDataFoundException(name: String) : RuntimeException("No data found: $name")