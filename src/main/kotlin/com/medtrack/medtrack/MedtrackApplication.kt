package com.medtrack.medtrack

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MedtrackApplication

fun main(args: Array<String>) {
	runApplication<MedtrackApplication>(*args)
}
