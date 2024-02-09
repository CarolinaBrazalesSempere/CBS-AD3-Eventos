package eventos.modelo.dao;

import java.util.List;


import eventos.modelo.entitis.Tipo;

public interface TipoDao {
	List<Tipo> todos();
	Tipo findOne(int idTipo);
}
