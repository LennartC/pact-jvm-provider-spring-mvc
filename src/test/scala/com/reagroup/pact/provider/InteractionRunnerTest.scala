package com.reagroup.pact.provider

import au.com.dius.pact.model.{Interaction, Request, Response}
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification

class InteractionRunnerTest extends Specification with Mockito {

  "InteractionRunner" should {
    "find interactions by provider state" in {
      val interactions = Seq(
        Interaction("desc1", Some("providerState1"), mock[Request], mock[Response]),
        Interaction("desc2", Some("providerState2"), mock[Request], mock[Response]))
      val runner = createRunner(interactions)

      val found = runner.findInteractions("providerState2")
      found must have length 1
      found(0).description === "desc2"
    }
    "find empty list if not matching provider" in {
      val interactions = Seq(Interaction("desc1", Some("providerState1"), mock[Request], mock[Response]))
      val runner = createRunner(interactions)
      runner.findInteractions("different") === Nil
    }
  }

  private def createRunner(interactions: Seq[Interaction]) = new InteractionRunner with InteractionFileReader {
    class DummyTestClass
    override val testClass = classOf[DummyTestClass]
    override lazy val allInteractions: Seq[Interaction] = interactions
  }

}
