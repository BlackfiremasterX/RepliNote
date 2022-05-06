package com.deepdweller.replinote.room

import androidx.room.Ignore

sealed class Item(@Ignore val viewType: ViewType)