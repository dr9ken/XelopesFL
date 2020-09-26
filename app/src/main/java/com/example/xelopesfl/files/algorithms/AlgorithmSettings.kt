package com.example.xelopesfl.files.algorithms

import org.eltech.ddm.inputdata.MiningInputStream
import org.eltech.ddm.inputdata.file.csv.CsvParsingSettings
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream
import java.io.Serializable

class AlgorithmSettings : Serializable {

    lateinit var paths : ArrayList<String>

    lateinit var readType : String

    lateinit var parsingSettings: CsvParsingSettings

    lateinit var target: String

    lateinit var stream: MiningInputStream

}