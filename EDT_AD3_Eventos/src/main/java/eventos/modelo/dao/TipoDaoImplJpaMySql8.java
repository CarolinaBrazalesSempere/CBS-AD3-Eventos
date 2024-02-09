package eventos.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Tipo;
import eventos.modelo.repository.TipoRepository;

@Repository
public class TipoDaoImplJpaMySql8 implements TipoDao {
	
	@Autowired
	private TipoRepository trepo;
	
	@Override
	public List<Tipo> todos() {
		
		return trepo.findAll();
	}

	@Override
	public Tipo findOne(int idTipo) {
		return trepo.findById(idTipo).orElse(null);
	}

	

}
