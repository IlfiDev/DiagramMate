package org.ilfidev.diagram_mate

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform