package com.tbf.cibercolegios.api.routes.services.users;

import static org.apache.commons.lang3.StringUtils.defaultString;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.upperCase;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbf.cibercolegios.api.routes.models.users.ImportViewModel;
import com.tbf.cibercolegios.api.routes.web.utils.FacesMessages;

import lombok.val;

@Service
public class DireccionesBatchService {

	private static final int NUMERO_MAXIMO_FILAS_VACIAS = 10;

	private static final int NUMERO_MAXIMO_FILAS = 10000;

	private static final String[] HEADERS = { "TIPO DE DOCUMENTO", "NÚMERO DE DOCUMENTO", "NOMBRES", "APELLIDOS",
			"DEPARTAMENTO AM", "MUNICIPIO AM", "DIRECCIÓN AM", "DEPARTAMENTO PM", "MUNICIPIO PM", "DIRECCIÓN PM" };

	@Autowired
	private ExcelWorkSheetReader reader;

	@Autowired
	private DireccionesMapService mapService;

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	public boolean load(InputStream is, List<ImportViewModel> models) throws IOException {
		val data = read(is);
		models.clear();

		boolean success = checkColumns(data);
		if (success) {
			models.addAll(asModels(data));

			success = checkNotEmptyFields(models);
			if (success) {
				success = mapService.map(models);
			}
		}

		return success;
	}

	// -----------------------------------------------------------------------------------
	// --
	// -----------------------------------------------------------------------------------
	private List<List<String>> read(InputStream is) throws IOException {
		int sheetIndex = 0;
		int rowStart = 0;
		int colStart = 0;
		int rowEnd = NUMERO_MAXIMO_FILAS;
		int colEnd = HEADERS.length;
		int maxEmptyRows = NUMERO_MAXIMO_FILAS_VACIAS;

		val result = reader.read(is, sheetIndex, rowStart, colStart, rowEnd, colEnd, maxEmptyRows);

		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- CHECK COLUMNS REQUIRED
	// -----------------------------------------------------------------------------------
	private boolean checkColumns(List<List<String>> data) {
		val errores = new ArrayList<String>();
		boolean result = reader.checkColumns(data, HEADERS, errores);

		for (val error : errores) {
			FacesMessages.error(error);
		}

		return result;
	}

	// -----------------------------------------------------------------------------------
	// -- AS MODELS
	// -----------------------------------------------------------------------------------
	private List<ImportViewModel> asModels(List<List<String>> data) {
		val result = new ArrayList<ImportViewModel>();
		int n = data.size();
		for (int index = 1; index < n; index++) {
			val row = data.get(index);
			val model = new ImportViewModel();

			int i = 0;
			model.setIndex(index);
			model.setCodigoTipoIdentificacionId(getField(row, i++));
			model.setNumeroIdentificacion(getField(row, i++));
			model.setNombres(getField(row, i++));
			model.setApellidos(getField(row, i++));
			model.setDepartamentoAm(getField(row, i++));
			model.setCiudadAm(getField(row, i++));
			model.setDireccionAm(getField(row, i++));
			model.setDepartamentoPm(getField(row, i++));
			model.setCiudadPm(getField(row, i++));
			model.setDireccionPm(getField(row, i++));
			model.setErrores(new ArrayList<>());

			if (!model.isEmpty()) {
				result.add(model);
			}
		}

		return result;
	}

	private String getField(List<String> a, int index) {
		return defaultString(upperCase(a.get(index))).trim();
	}

	// -----------------------------------------------------------------------------------
	// -- CHECK EMPTY FILEDS
	// -----------------------------------------------------------------------------------
	private boolean checkNotEmptyFields(List<ImportViewModel> models) {
		boolean result = true;

		for (val model : models) {
			val empties = new ArrayList<String>();

			int i = 0;
			addEmptyField(empties, HEADERS[i++], model.getCodigoTipoIdentificacionId());
			addEmptyField(empties, HEADERS[i++], model.getNumeroIdentificacion());
			addEmptyField(empties, HEADERS[i++], model.getNombres());
			addEmptyField(empties, HEADERS[i++], model.getApellidos());

			boolean emptyAm = isEmpty(model.getDepartamentoAm()) && isEmpty(model.getCiudadAm())
					&& isEmpty(model.getDireccionAm());
			boolean emptyPm = isEmpty(model.getDepartamentoPm()) && isEmpty(model.getCiudadPm())
					&& isEmpty(model.getDireccionPm());

			if ((emptyAm && emptyPm) || !emptyAm) {
				addEmptyField(empties, HEADERS[i++], model.getDepartamentoAm());
				addEmptyField(empties, HEADERS[i++], model.getCiudadAm());
				addEmptyField(empties, HEADERS[i++], model.getDireccionAm());
			} else {
				i += 3;
			}

			if ((emptyAm && emptyPm) || !emptyPm) {
				addEmptyField(empties, HEADERS[i++], model.getDepartamentoPm());
				addEmptyField(empties, HEADERS[i++], model.getCiudadPm());
				addEmptyField(empties, HEADERS[i++], model.getDireccionPm());
			} else {
				i += 3;
			}

			if (!empties.isEmpty()) {
				result = false;
				val e = String.join(",", empties);
				val msg = String.format("Los siguientes campos son requeridos y estan vacíos: %s.", e);
				model.getErrores().add(msg);
			}
		}

		return result;
	}

	private void addEmptyField(List<String> empties, String field, String value) {
		if (isEmpty(value)) {
			empties.add(field);
		}
	}
}