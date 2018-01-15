package ar.gov.pjn.suat.transformers;

import ar.gov.pjn.suat.persistence.domain.Grupo;

public class GrupoTransformer {

	public static Grupo updateFromInput(Grupo grupoInput, Grupo grupoDest) {
		grupoDest.setDescripcion(grupoInput.getDescripcion());
		return grupoDest;
	}
}
