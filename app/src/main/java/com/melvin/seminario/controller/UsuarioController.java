package com.melvin.seminario.controller;

import com.melvin.seminario.dao.DaoInternetUsuarios;
import com.melvin.seminario.model.User;
import com.melvin.seminario.util.ResultListener;

public class UsuarioController {

    public void verificarUsuario(String username, String password, ResultListener<Boolean> listenerView) {
        User user = new User(username, password);
        new DaoInternetUsuarios().obtenerUsuario(user, new ResultListener<User>() {
            @Override
            public void finish(User resultado) {
                if (resultado.getPassword() != null) {
                    if (resultado.getPassword().equals(password))
                        listenerView.finish(true);
                    else
                        listenerView.finish(false);
                }else
                    listenerView.finish(false);
            }
        });
    }

    public void recuperarUsuario(String username, ResultListener<User> listenerView){
        User user = new User(username);
        new DaoInternetUsuarios().obtenerUsuario(user, listenerView);
    }

    public void verificarMail(String mail, ResultListener<Boolean> listenerView) {
        User user = new User(mail);
        new DaoInternetUsuarios().obtenerUsuario(user,
                resultado -> {
                    if (resultado.getUsername() != null) {
                        if (resultado.getUsername().equals(mail)) {
                            listenerView.finish(false);
                        } else {
                            listenerView.finish(true);
                        }
                    } else {
                        listenerView.finish(true);
                    }
                });
    }

    public void crearUsuario(User usuario, ResultListener<Boolean> listenerView){
        new DaoInternetUsuarios().crearUsuario(usuario, listenerView);
    }
}
