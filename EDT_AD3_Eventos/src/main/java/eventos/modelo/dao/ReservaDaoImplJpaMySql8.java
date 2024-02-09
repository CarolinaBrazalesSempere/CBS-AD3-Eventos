package eventos.modelo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Usuario;
import eventos.modelo.repository.ReservaRepository;

@Repository
public class ReservaDaoImplJpaMySql8 implements ReservaDao {

	@Autowired
	private ReservaRepository rerepo;

	@Override
	public int insertOne(Reserva reserva) {
		try {
			Reserva reservation = rerepo.save(reserva);
			if (reservation != null) {
				return 1;
			}
		} catch (DataAccessException ex) {
			ex.printStackTrace();
		}
		return 0;
	}


	@Override
	public int delete(int idReserva) {
		try {
			rerepo.deleteById(idReserva);

			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;

	}

	@Override
	public List<Reserva> reserPorUsuario(Usuario usuario) {
		
		return rerepo.reservaPorUsuario(usuario);
	}


	@Override
	public int eventoReservadoPorUsuario(String username, int idEvento) {
		List<Reserva> reservas = rerepo.eventoReservadoPorUsuario(username, idEvento);
		int cantidadEventos = 0;
		
		for (Reserva res : reservas){
			cantidadEventos += res.getCantidad();
		}
		
		return cantidadEventos;
	}


	@Override
	public int reservasPorEvento(int idEvento) {
		List<Reserva> reservas = rerepo.reservasPorEvento(idEvento);
		int reservasTotales = 0;
		
		for (Reserva reserva : reservas) {
			reservasTotales += reserva.getCantidad();
		}
		return reservasTotales;
	}


	


	


	

	

}
