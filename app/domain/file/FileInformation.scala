package domain.file

import scala.reflect.io.File

case class FileInformation(key: String,
                           filename: String,
                           contentType: Option[String],
                           file: File,
                           fileSize: Long,
                           dispositionType: String)
