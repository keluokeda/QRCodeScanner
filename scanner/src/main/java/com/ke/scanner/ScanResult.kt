package com.ke.scanner

data class ScanResult(
    val status: ScanResultStatus,
    val text: String? = null
)

enum class ScanResultStatus {
    Success,
    Cancel,
    NoCameraPermission
}

