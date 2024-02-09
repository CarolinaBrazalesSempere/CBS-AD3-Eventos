package eventos.modelo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventos.modelo.entitis.Reserva;
import eventos.modelo.entitis.Usuario;

public interface ReservaRepository extends JpaRepository <Reserva, Integer> {
	
	@Query("SELECT r FROM Reserva r WHERE r.usuario = ?1")
	public List<Reserva> reservaPorUsuario(Usuario usuario);
	
	@Query("SELECT r FROM Reserva r WHERE r.usuario.username = ?1 AND r.evento.idEvento = ?2")
	public List<Reserva> eventoReservadoPorUsuario(String username, int idEvento);
	
	@Query("select r FROM Reserva r WHERE r.evento.idEvento = ?1")
	List<Reserva> reservasPorEvento(int idEvento);

}
