package eventos.modelo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Usuario;
import eventos.modelo.repository.UsuarioRepository;

@Repository
public class UsuarioDaoImplJpaMySql8 implements UsuarioDao {
	
	@Autowired
	private UsuarioRepository urepo;
	
	@Override
	public Usuario findById(String username) {
		
		 return urepo.findById(username).orElse(null);
	}

	@Override
	public boolean insertUser(Usuario usuario) {
		if(findById(usuario.getUsername()) == null){
			urepo.save(usuario);
			return true;
		}
		return false;
	}

}
