package ar.gov.pjn.suat.transformers;

import ar.gov.pjn.suat.persistence.domain.Fuero;

public class FueroTransformer {

	public static Fuero updateFromInput(Fuero fueroInput, Fuero fueroDest) {
		fueroDest.setDescripcion(fueroInput.getDescripcion());
		return fueroDest;
	}

}
