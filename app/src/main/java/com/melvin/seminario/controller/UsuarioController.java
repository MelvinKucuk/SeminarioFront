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
}
