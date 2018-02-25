package com.ucb.appin.util;

import java.io.Serializable;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by tecnosim on 1/27/2018.
 */

public final class ConstInVirtual implements Serializable {
    public static final String BASE_URL = "http://labolsita.esy.es/";
    //public static final String BASE_URL = "http://192.168.43.8/";
    public static final String FIELD_ID = "id";

    public static final String LIST_AVISOS = "inmobiliariavirtual/api/controllers/aviso/listas.php/";
    public static final String SET_AVISOS = "inmobiliariavirtual/api/controllers/aviso/insertars.php/";
    public static final String LIST_TIPO_AVISOS = "inmobiliariavirtual/api/controllers/tipoaviso/listas.php/";
    public static final String LIST_TRANSACCION_AVISOS = "inmobiliariavirtual/api/controllers/transaccionaviso/listas.php";
    public static final String SET_CUENTAS = "inmobiliariavirtual/api/controllers/cuenta/insertar.php/";
    public static final String UPDATE_CUENTAS = "inmobiliariavirtual/api/controllers/cuenta/editar.php/";

    public static final String DEFAULT_DATA_ERROR = "Datos incorrectos";
    public static final String CARPETA_RAIZ = "misImagenesPrueba/";
    public static final String RUTA_IMAGEN = CARPETA_RAIZ + "misFotos";

    public static final int COD_SELECCIONA = 10;
    public static final int COD_FOTO = 20;
    public static final double DEFAULT_LATITUDE = -17.390443;
    public static final double DEFAULT_LONGITUDE = -66.170058;

    public static final int DEFAULT_ZOOM = 14;

    public static final String INCORRECT_OPERATION = "Registro correcto";
    public static final String CORRECT_OPERATION = "No se pudo registrar";
    public static final int REMOTE_LOCATION = 1;
    public static final int LOCAL_LOCATION = 0;
    public static final int BOTH_LOCATION = 2;

    public static final int INDEX_FRAGMENT_AVISOS = 0;
    public static final String NAME_PACKAGE = "com.ucb.appin";
    public static final long DEFAULT_WAIT = 30;
    public static final long WAIT_THYRTY_SECONDS = 30000;
    public static final int ADD_OPERACION = 1;
    public static final int STOP_STRESS_USER_MULTIPLES_CLICK = 200;
    public static final int EDIT_OPERATION = 2;
    public static final String ACTIVE = "A";
    public static final String INACTIVE = "X";
    public static final long WAIT_FIFTEEN_SECONDS = 15000;
    public static final long WAIT_FIVE_SECONDS = 5000;
    public static final String MESSAGE_OFFLINE = "Necesita una conexion activa a internet para realizar esta operacion";
    public static final String MESSAGE_NOTHING_ACCOUNT = "Ud. No tiene una cuenta registrada, Registre una cuentas(Toque en opciones)";
    public static final String TIPO_AVISO_NOTHING = "Debe seleccionar Tipo Aviso";
    public static final String TRANSACCION_AVISO_NOTHING = "Debe Seleccionar Tipo de Transaccion de Aviso";
    public static final String MESSAGE_OK_OPERATION = "Regitrado Correctamente";
    public static final String MESSAGE_ERROR_OPERATION = "No se Pudo Registrar";
    public static final String WITHOUT_DATE = "Sin Fecha";
    public static final int SECOND_TO_REVIEW_NEW_DATA = 7;
    public static final String MESSAGE_OK_OPERATION_WAIT = "Operacion en Progreso... En unos momentos terminaremos de procesar la operacion";


    public static int INDEX_FRAGMENT_MAPA = 1;
    public static int INDEX_FRAGMENT_MY_AVISOS = 2;

    public static CompositeDisposable DISPOSABLES = new CompositeDisposable();
}
