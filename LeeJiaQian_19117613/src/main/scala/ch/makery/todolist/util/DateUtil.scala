package ch.makery.address.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

object DateUtil {
  val DATE_PATTERN = "dd.MM.yyyy"
  val DATE_FORMATTER =  DateTimeFormatter.ofPattern(DATE_PATTERN)

//  Returns formatted date to String.
//  parameter date to be returned as a string
  implicit class DateFormater (val date : LocalDate){
    def asString : String = {
      if (date == null) {
//        Returns null if the String could not be converted.
        return null;
      }
//      return the date object
      return DATE_FORMATTER.format(date);
    }
  }

  implicit class StringFormater (val data : String) {
//    Converts a String in the format of the defined DateUtil to a LocalDate object
    def parseLocalDate : LocalDate = {
      try {
        LocalDate.parse(data, DATE_FORMATTER)
      } catch {
        case  e: DateTimeParseException => null
      }
    }
    def isValid : Boolean = {
      data.parseLocalDate != null
    }
  }
}
