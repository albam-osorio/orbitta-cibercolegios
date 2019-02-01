package com.tbf.cibercolegios.api.routes.controllers.routes.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.tbf.cibercolegios.api.routes.controllers.routes.models.PasajeroViewModel;

import lombok.val;

@FacesConverter(value = "pasajeroPickListConverter")
public class PasajeroViewModelPickListConverter implements Converter<PasajeroViewModel> {

	@Override
	public PasajeroViewModel getAsObject(FacesContext context, UIComponent component, String value) {
		PasajeroViewModel ret = null;

		if (component instanceof PickList) {
			Object dualList = ((PickList) component).getValue();

			@SuppressWarnings("unchecked")
			DualListModel<PasajeroViewModel> dl = (DualListModel<PasajeroViewModel>) dualList;

			for (val o : dl.getSource()) {
				String id = "" + o.getUsuarioId();
				if (value.equals(id)) {
					ret = o;
					break;
				}
			}
			if (ret == null) {
				for (val o : dl.getTarget()) {
					String id = "" + o.getUsuarioId();
					if (value.equals(id)) {
						ret = o;
						break;
					}
				}
			}
		}
		return ret;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, PasajeroViewModel value) {
		String str = String.valueOf(value.getUsuarioId());
		return str;
	}
}
