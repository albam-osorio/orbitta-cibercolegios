package co.com.orbitta.cibercolegios.rutas.service.impl.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.orbitta.cibercolegios.rutas.domain.readonly.Usuario;
import co.com.orbitta.cibercolegios.rutas.dto.readonly.UsuarioDto;
import co.com.orbitta.cibercolegios.rutas.repository.readonly.UsuarioRepository;
import co.com.orbitta.cibercolegios.rutas.service.api.readonly.UsuarioService;
import co.com.orbitta.core.services.crud.impl.QueryServiceImpl;
import lombok.val;

@Service
public class UsuarioQueryServiceImpl extends QueryServiceImpl<Usuario, UsuarioDto, Integer> implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	protected UsuarioRepository getRepository() {
		return repository;
	}

	@Override
	protected UsuarioDto asModel(Usuario entity) {
		val result = new UsuarioDto();

		result.setId(entity.getId());
		result.setNombre(entity.getNombre());
		result.setApellido(entity.getApellido());

		return result;
	}
}