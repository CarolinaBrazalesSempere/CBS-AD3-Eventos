package eventos.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Perfil;
import eventos.modelo.repository.PerfilRepository;

@Repository
public class PerfilDaoImplJpaMySql8 implements PerfilDao{
	
	@Autowired
	private PerfilRepository perfrepo;

	@Override
	public Perfil findById(int idPerfil) {
		
		return perfrepo.findById(idPerfil).orElse(null);
	}

}
