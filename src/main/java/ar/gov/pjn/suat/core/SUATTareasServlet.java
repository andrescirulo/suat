package ar.gov.pjn.suat.core;

import javax.servlet.http.HttpServlet;


public class SUATTareasServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2415996554432441957L;

	public void init() {
		SUATTareaProcessor.getInstance().init();
	}


}
