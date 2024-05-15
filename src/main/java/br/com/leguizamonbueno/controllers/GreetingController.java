package br.com.leguizamonbueno.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.leguizamonbueno.model.Usuario;
import br.com.leguizamonbueno.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
class GreetingsController {

    @Autowired /*CD ou CDI Injeção de Dependencia */
    private UsuarioRepository usuarioRepository;

    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostranome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso springn boot api " + name + "!";
    }

    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setIdade(43);
        usuarioRepository.save(usuario);

        return "Olá Mundo" + usuario.getNome();
    }

    @GetMapping(value = "listartodos")
    @ResponseBody /* Retorna os dados para o corpo da resposta Json*/
    public ResponseEntity<List<Usuario>> listaUsuario() {

        List<Usuario> usuarios = usuarioRepository.findAll(); /* consulta ao banco de dados */
        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); /* Retorna os dados em json*/

    }

    @PostMapping(value = "salvar") //URL
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { /* RequestBody - Recebe os dados para salvar */

        Usuario user = usuarioRepository.save(usuario);
        return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);

    }

    @DeleteMapping(value = "delete") //URL
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<String> delete(@RequestParam Long idUser) { /* RequestParam - parametro id */

        usuarioRepository.deleteById(idUser);
        return new ResponseEntity<String>("User deletado com sucesso", HttpStatus.OK);

    }

    @GetMapping(value = "buscaruserid") //URL
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<Usuario> buscaruserid(@RequestParam(name="iduser") Long idUser) { /* RequestParam - parametro id */

        Usuario usuario = usuarioRepository.findById(idUser).get();
        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);

    }

    @GetMapping(value = "buscarpornome") //URL
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name="name") String name) { /* RequestParam - parametro id */

        List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());
        return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);

    }

    @GetMapping(value = "findUsersWithPartOfName") //URL
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<List<Usuario>> findUsersWithPartOfName(@RequestParam(name="name") String name) { /* RequestParam - parametro id */

        List<Usuario> usuario = usuarioRepository.findUsersWithPartOfName(name);
        return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);

    }


    @PutMapping(value = "atualizar") //URL
    @ResponseBody /* Descrição da resposta*/
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { /* RequestBody - Recebe os dados para salvar */

        if(usuario.getId() == null) {
            return new ResponseEntity<String>("ID não foi informado!", HttpStatus.OK);
        }

        Usuario user = usuarioRepository.saveAndFlush(usuario);
        return new ResponseEntity<Usuario>(user, HttpStatus.OK);

    }

}
