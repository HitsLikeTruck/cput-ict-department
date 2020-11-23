package environment.util

import java.time.{LocalDateTime, ZoneId}
import java.util.{Date, Random}

import com.typesafe.config.ConfigFactory
import org.apache.commons.lang3.RandomStringUtils

import scala.util.{Failure, Success, Try}

/**
 * Created by hashcode on 2015/11/07.
 */
object Util {

  import java.net._

  val config = ConfigFactory.load()
  val browserTimeOut = config.getInt("browser.timeout")


  def md5Hash(text: String): String = {
    val hash = text + InetAddress.getLocalHost.getHostName
    java.security.MessageDigest.getInstance("MD5").digest(hash.getBytes()).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }
  }

  def cleanMd5Hash(text: String): String = {
    java.security.MessageDigest.getInstance("MD5").digest(text.getBytes()).map(0xFF & _).map {
      "%02x".format(_)
    }.foldLeft("") {
      _ + _
    }
  }

  def getLocalDateTimeFromDate(date: Date): LocalDateTime = {
    date.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDateTime()
  }

  def codeGen(name: String): String = {
    val code = generateOrganisationCode(name)
      .toCharArray
      .sortWith(_ < _)
      .mkString("").toUpperCase

    val salt = getSalt()
      .toCharArray
      .sortWith(_ < _)
      .mkString("").toUpperCase
    (code + "-" + salt)
  }

  private def generateOrganisationCode(name: String): String = {
    val count: Int = 4
    val useLetters: Boolean = true
    val useNumbers: Boolean = false
    val choseFrom = name.toCharArray
    RandomStringUtils.random(count, 0, 0, useLetters, useNumbers, choseFrom, new Random())
  }

  private def getSalt(): String = {
    val length: Int = 5
    val useLetters: Boolean = true
    val useNumbers: Boolean = true
    RandomStringUtils.random(length, useLetters, useNumbers)
  }

  def getIntFromString(value: String): Int = {
    Try(Integer.parseInt(value)) match {
      case Success(ans) => ans
      case Failure(ex) => 0
    }
  }

  def slugify(input: String): String = {
    import java.text.Normalizer
    Normalizer.normalize(input, Normalizer.Form.NFD)
      .replaceAll("[^\\w\\s-]", "") // Remove all non-word, non-space or non-dash characters
      .replace('-', ' ') // Replace dashes with spaces
      .trim // Trim leading/trailing whitespace (including what used to be leading/trailing dashes)
      .replaceAll("\\s+", "-") // Replace whitespace (including newlines and repetitions) with single dashes
      .toLowerCase // Lowercase the final results
  }

  //  def getMetaKeywords(title: String) = {
  //    val cleanedWords = FilterService.removeStopWords(title)
  //    cleanedWords.split(' ').map(_.capitalize).mkString(",")
  //  }

  def getMetDecription(article: String) = {
    if (article.length <= 156) {
      article
    } else {
      val description = article.substring(0, 156)
      description.split(' ').map(_.capitalize).mkString(" ")
    }
  }

}
