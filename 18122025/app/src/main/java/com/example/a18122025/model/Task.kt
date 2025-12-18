package com.example.a18122025.model

class Task {
    // Getters and Setters
    var id: Int = 0
    var title: String? = null
    var description: String? = null
    var date: String? = null
    var status: Int = 0 // 0: chưa hoàn thành, 1: đã hoàn thành
    var userId: Int = 0

    constructor()

    constructor(
        id: Int,
        title: String?,
        description: String?,
        date: String?,
        status: Int,
        userId: Int
    ) {
        this.id = id
        this.title = title
        this.description = description
        this.date = date
        this.status = status
        this.userId = userId
    }
}