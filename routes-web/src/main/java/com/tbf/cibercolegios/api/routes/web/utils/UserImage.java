package com.tbf.cibercolegios.api.routes.web.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbf.cibercolegios.api.ciber.services.api.CiberService;

import lombok.val;

@Component
@ApplicationScoped
public class UserImage {

	@Autowired
	private CiberService ciberService;

	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the view. Return a stub StreamedContent so that it will
			// generate right URL.
			return new DefaultStreamedContent();
		} else {
			// So, browser is requesting the image. Return a real StreamedContent with the
			// image bytes.
			String id = context.getExternalContext().getRequestParameterMap().get("id");

			val optional = ciberService.findImagenUsuarioByUsuarioId(Integer.valueOf(id));
			if (optional.isPresent()) {
				return new DefaultStreamedContent(new ByteArrayInputStream(optional.get()));
			} else {
				return new DefaultStreamedContent();
			}
		}
	}
}
