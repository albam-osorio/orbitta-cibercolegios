package com.tbf.cibercolegios.api.routes.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.orderlist.OrderList;

import com.tbf.cibercolegios.api.routes.models.routes.RutaEditDialogSortViewModel;

import lombok.val;

@FacesConverter(value = "rutaEditDialogSortOrderListConverter")
public class RutaEditDialogSortViewModelPickListConverter implements Converter<RutaEditDialogSortViewModel> {

	@Override
	public RutaEditDialogSortViewModel getAsObject(FacesContext context, UIComponent component, String value) {
		RutaEditDialogSortViewModel result = null;

		if (component instanceof OrderList) {
			Object list = ((OrderList) component).getValue();

			@SuppressWarnings("unchecked")
			List<RutaEditDialogSortViewModel> ol = (List<RutaEditDialogSortViewModel>) list;
			
			Integer id = Integer.parseInt(value);
			val optional = ol.stream().filter(a -> a.getDireccion().getId().equals(id)).findFirst();
			if (optional.isPresent()) {
				result = optional.get();
			}
		}
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, RutaEditDialogSortViewModel value) {
		String str = String.valueOf(value.getDireccion().getId());
		return str;
	}
}
