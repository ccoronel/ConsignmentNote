package com.eahc.consignmentnote.entities

import java.util.*

class ConsignmentWS (
    val unit: String,
    val user: String,
    val consignment: String ,
    val PDF: String,
    val storedDate: Date,
    var selected: Boolean
)