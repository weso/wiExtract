package es.weso.wiExtract

case class Observation(
    countryCode: String,
    countryName: String,
    year: String,
    indicatorCode: String,
    value: Float)