package com.itsoeh.jortiz.proyectoaaisic.accesoADatos;

public interface Appi {
    String URL = "https://atributosegresoitsoeh5.000webhostapp.com//evaluacionAE/Api.php";
    String GUARDAR=URL+"?apicall=guardarEstudiante";
    String BUSCARCONTRASENIA=URL+"?apicall=buscarContraseniaEstudiante";
    String OBTENERIDESTUDIANTE=URL+"?apicall=obtenerIdEstudiante";
    String ACTUALIZAR_NOMBRE=URL+"?apicall=actualizarNombreEstudiante";
    String ACTUALIZAR_CORREO=URL+"?apicall=actualizarCorreoEstudiante";
    String ACTUALIZAR_PASSWORD=URL+"?apicall=actualizarPasswordEstudiante";
    String AGREGAR_ESTUDIANTE=URL+"?apicall=guardarEstudianteAsignaturaSiExiste";
    String LISTAR_ASIGNATURA=URL+"?apicall=obtenerAsignaturasEstudiante";
    String VERIFICAR_CORREO=URL+"?apicall=verificarCorreoEstudiante";
    String ACTUALIZAR_PASSWORD_SIN_VERI=URL+"?apicall=actualizarPasswordSinVerificarEstudiante";

}
