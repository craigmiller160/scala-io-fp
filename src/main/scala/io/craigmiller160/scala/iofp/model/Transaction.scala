package io.craigmiller160.scala.iofp.model

import java.util.UUID

case class Transaction(
                        id: UUID,
                        personId: UUID,
                        typeId: UUID,
                        typeName: String,
                        amount: Double
                      )
