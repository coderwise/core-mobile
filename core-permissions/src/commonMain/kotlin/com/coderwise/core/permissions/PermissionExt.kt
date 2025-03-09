package com.coderwise.core.permissions

val Permission.Companion.LOCATION: Permission get() = LocationPermission

object LocationPermission : Permission

val Permission.Companion.CAMERA: Permission get() = CameraPermission

object CameraPermission : Permission

val Permission.Companion.MICROPHONE: Permission get() = MicrophonePermission

object MicrophonePermission : Permission

val Permission.Companion.STORAGE: Permission get() = StoragePermission

object StoragePermission : Permission

val Permission.Companion.BLUETOOTH: Permission get() = BluetoothPermission

object BluetoothPermission : Permission