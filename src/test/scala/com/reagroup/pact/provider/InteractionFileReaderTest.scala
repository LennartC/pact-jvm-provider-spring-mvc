package com.reagroup.pact.provider

import java.io.FileNotFoundException

import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class InteractionFileReaderTest extends Specification with Mockito {

  "InteractionFileReader" should {
    "read interactions from the JSON file" in {
      val reader = createReader(classOf[TestClass])
      reader.allInteractions must have length 4
      reader.allInteractions.map(_.providerState) === Seq(Some("normal"), Some("with-headers"), Some("with-cookies"), Some("with-array-body"))
    }
    "should report error if file is not found" in {
      val reader = createReader(classOf[TestClassWithFileNotFound])
      reader.allInteractions should throwA[FileNotFoundException]
    }
    "should report @PactFile missing if can't find it" in {
      val reader = createReader(classOf[TestClassWithoutPactFileAnnotation])
      reader.allInteractions should throwA[IllegalStateException]
    }
  }

  @PactFile("file:src/test/resources/interactions.json")
  class TestClass

  @PactFile("file:src/test/resources/not-found-file.json")
  class TestClassWithFileNotFound

  class TestClassWithoutPactFileAnnotation

  def createReader(cls: Class[_]) = new InteractionFileReader {
    override lazy val testClass: Class[_] = cls
  }
}
