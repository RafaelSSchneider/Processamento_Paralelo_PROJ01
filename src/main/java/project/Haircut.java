package project;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
enum Haircut {
  ZERO (2, "Máquina Zero"),
  MULLET (6, "Corte Mullet"),
  PONTAS (3, "Aparada nas Pontas"),
  BIGODE (1, "Deixar o Bigodin Finin"),
  BUZZED (7, "Corte Buzzed com degradê"),
  OLD_MONEY (5, "Corte Old Money");

  private final int timeToCut;
  private final String name;
}

