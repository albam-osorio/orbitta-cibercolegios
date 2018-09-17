package co.com.orbitta.cibercolegios.rutas.dto.tracking.acudiente;

import java.math.BigDecimal;

import co.com.orbitta.cibercolegios.rutas.dto.tracking.AbstractDatosRutaDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DatosParadaDto extends AbstractDatosRutaDto {

	private static final long serialVersionUID = 1L;

	private Integer usuarioRutaId;

	private Integer estudianteId;

	private String estudianteNombres;

	private String estudianteApellidos;

	private int totalParadas;

	private int secuencia;

	private String direccion;

	private BigDecimal paradaX;

	private BigDecimal paradaY;

	@Builder
	public DatosParadaDto(int rutaId, String codigo, String descripcion, String marca, String placa, String movil,
			int capacidad, int institucionId, String institucionNombre, BigDecimal institucionX,
			BigDecimal institucionY, int monitorId, String monitorNombres, String monitorApellidos, int conductorId,
			String conductorNombres, String conductorApellidos, boolean activa, Integer usuarioRutaId,
			Integer estudianteId, String estudianteNombres, String estudianteApellidos, int totalParadas, int secuencia,
			String direccion, BigDecimal paradaX, BigDecimal paradaY) {
		super(rutaId, codigo, descripcion, marca, placa, movil, capacidad, institucionId, institucionNombre,
				institucionX, institucionY, monitorId, monitorNombres, monitorApellidos, conductorId, conductorNombres,
				conductorApellidos, activa);
		this.usuarioRutaId = usuarioRutaId;
		this.estudianteId = estudianteId;
		this.estudianteNombres = estudianteNombres;
		this.estudianteApellidos = estudianteApellidos;
		this.totalParadas = totalParadas;
		this.secuencia = secuencia;
		this.direccion = direccion;
		this.paradaX = paradaX;
		this.paradaY = paradaY;
	}
}
