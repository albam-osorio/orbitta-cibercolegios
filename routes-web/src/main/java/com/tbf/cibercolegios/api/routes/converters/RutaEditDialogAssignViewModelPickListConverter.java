package com.tbf.cibercolegios.api.routes.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.tbf.cibercolegios.api.routes.models.routes.RutaEditDialogAssignViewModel;

import lombok.val;

@FacesConverter(value = "rutaEditDialogAssignViewModelPickListConverter")
public class RutaEditDialogAssignViewModelPickListConverter implements Converter<RutaEditDialogAssignViewModel> {

	@Override
	public RutaEditDialogAssignViewModel getAsObject(FacesContext context, UIComponent component, String value) {
		RutaEditDialogAssignViewModel result = null;

		if (component instanceof PickList) {
			Object dualList = ((PickList) component).getValue();

			@SuppressWarnings("unchecked")
			DualListModel<RutaEditDialogAssignViewModel> dl = (DualListModel<RutaEditDialogAssignViewModel>) dualList;

			for (val o : dl.getSource()) {
				String id = "" + o.getTrayecto().getId();
				if (value.equals(id)) {
					result = o;
					break;
				}
			}
			if (result == null) {
				for (val o : dl.getTarget()) {
					String id = "" + o.getTrayecto().getId();
					if (value.equals(id)) {
						result = o;
						break;
					}
				}
			}
		}
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, RutaEditDialogAssignViewModel value) {
		String str = String.valueOf(value.getTrayecto().getId());
		return str;
	}
}
