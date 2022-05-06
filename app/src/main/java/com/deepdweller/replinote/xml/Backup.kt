package com.deepdweller.replinote.xml

import com.deepdweller.replinote.room.BaseNote

class Backup(
    val baseNotes: List<BaseNote>,
    val deletedNotes: List<BaseNote>,
    val archivedNotes: List<BaseNote>,
    val labels: HashSet<String>
)