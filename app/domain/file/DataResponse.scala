package domain.file

import java.nio.ByteBuffer

case class DataResponse(file: StoredFile,
                        byteBuffers:  List[ByteBuffer])
