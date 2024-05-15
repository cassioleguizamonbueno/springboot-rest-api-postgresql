package br.com.leguizamonbueno.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.leguizamonbueno.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select u from Usuario u where upper(trim(u.nome)) like %?1%")
    List<Usuario> buscarPorNome(String name);

    @Query("SELECT u.nome FROM Usuario u WHERE u.nome LIKE %?1%")
    List<Usuario> findUsersWithPartOfName(@Param("name") String name);

}
