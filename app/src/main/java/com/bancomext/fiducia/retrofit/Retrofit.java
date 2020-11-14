package com.bancomext.fiducia.retrofit;

import com.bancomext.fiducia.model.service.envio.EnvioAtencion;
import com.bancomext.fiducia.model.service.envio.EnvioEnvia;
import com.bancomext.fiducia.model.service.envio.EnvioLogin;
import com.bancomext.fiducia.model.service.envio.EnvioMovimientos;
import com.bancomext.fiducia.model.service.envio.EnvioPatrimonios;
import com.bancomext.fiducia.model.service.envio.EnvioPatrimoniosIndiv;
import com.bancomext.fiducia.model.service.envio.EnvioReportMovFiltrado;
import com.bancomext.fiducia.model.service.envio.EnvioReporte;
import com.bancomext.fiducia.model.service.envio.EnvioSaldos;
import com.bancomext.fiducia.model.service.envio.EnvioValora;
import com.bancomext.fiducia.model.service.recibo.ReciboAtencion;
import com.bancomext.fiducia.model.service.recibo.ReciboEnvia;
import com.bancomext.fiducia.model.service.recibo.ReciboLogin;
import com.bancomext.fiducia.model.service.recibo.ReciboMovimientos;
import com.bancomext.fiducia.model.service.recibo.ReciboPatrimonios;
import com.bancomext.fiducia.model.service.recibo.ReciboPatrimoniosIndiv;
import com.bancomext.fiducia.model.service.recibo.ReciboReportMovFiltrado;
import com.bancomext.fiducia.model.service.recibo.ReciboReporte;
import com.bancomext.fiducia.model.service.recibo.ReciboSaldos;
import com.bancomext.fiducia.model.service.recibo.ReciboValora;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Retrofit {

/*--------------------------------------------Produccion------------------------------------------------*/
    //login
    @POST("appFiducia/login")
    @Headers({"Content-Type: application/json"})
    Call<ReciboLogin> obtenerProdution(@Body EnvioLogin datos);

    //servicio de fideicomisos
    @POST("appFiducia/atencion")
    @Headers("Content-Type: application/json")
    Call<List<ReciboAtencion>> obtenerProdution(@Body EnvioAtencion datos);

    //motivos
    @POST("appFiducia/movto")
    @Headers("Content-Type: application/json")
    Call<List<ReciboMovimientos>> obtenerProdution(@Body EnvioMovimientos datos);

    //saldos
    @POST("appFiducia/saldo")
    @Headers("Content-Type: application/json")
    Call<List<ReciboSaldos>> obtenerProdution(@Body EnvioSaldos datos);

    //patrimonios
    @POST("appFiduciaResources/patrimonio")
    @Headers("Content-Type: application/json")
    Call<ReciboPatrimonios> obtenerProdution(@Body EnvioPatrimonios datos);

    //patrimonios individual
    @POST("appFiduciaResources/patrimonioIndiv")
    @Headers("Content-Type: application/json")
    Call<ReciboPatrimoniosIndiv> obtenerProdution(@Body EnvioPatrimoniosIndiv datos);

    //envia
    @POST("appFiducia/envia")
    @Headers("Content-Type: application/json")
    Call<ReciboEnvia> obtenerProdution(@Body EnvioEnvia datos);

    //enviaMovTo
    @POST("appFiducia/envia/movimiento")
    @Headers("Content-Type: application/json")
    Call<ReciboReportMovFiltrado> reporteEnviaMovimiento(@Body EnvioReportMovFiltrado datos);

    //enviaMovTo
    @POST("appFiducia/envia/saldo")
    @Headers("Content-Type: application/json")
    Call<ReciboReporte> reporteEnviaSaldo(@Body EnvioReporte datos);


    //enviaMovTo
    @POST("appFiducia/envia/patrimonio")
    @Headers("Content-Type: application/json")
    Call<ReciboReporte> reporteEnviaPatrimonio(@Body EnvioReporte datos);

    //enviaPatrimonio
    @POST("appFiducia/envia/patrimonio")
    @Headers("Content-Type: application/json")
    Call<ReciboPatrimonios> reporteEnviaPatrimonio(@Body EnvioPatrimonios response);

    //valorar
    @POST("appFiducia/valorar")
    @Headers("Content-Type: application/json")
    Call<ReciboValora> valoraRequest(@Body EnvioValora datos);

    //ReporteFiltrado
    @POST("appFiducia/envia/movimiento")
    @Headers("Content-Type: application/json")
    Call<ReciboReportMovFiltrado> reportFiltrado(@Body EnvioReportMovFiltrado data);
/*-----------------------------------------Test---------------------------------------------*/
    @POST("appFiduciaResources/login")
    @Headers("Content-Type: application/json")
    Call<ReciboLogin> obtenerPruebas(@Body EnvioLogin datos);

    //servicio de fideicomisos
    @POST("appFiduciaResources/atencion")
    @Headers("Content-Type: application/json")
    Call<List<ReciboAtencion>> obtenerPruebas(@Body EnvioAtencion datos);

    @POST("appFiduciaResources/movto")
    @Headers({"Content-Type: application/json"})
    Call<List<ReciboMovimientos>>obtenerPruebas(@Body EnvioMovimientos datos);

    @POST("appFiduciaResources/saldo")
    @Headers("Content-Type: application/json")
    Call<List<ReciboSaldos>> obtenerPruebas(@Body EnvioSaldos datos);

    @POST("appFiduciaResources/patrimonio")
    @Headers("Content-Type: application/json")
    Call<ReciboPatrimonios> obtenerPruebas(@Body EnvioPatrimonios datos);

    @POST("appFiduciaResources/patrimonioIndiv")
    @Headers("Content-Type: application/json")
    Call<ReciboPatrimoniosIndiv> obtenerPruebas(@Body EnvioPatrimoniosIndiv datos);

    @POST("appFiduciaResources/envia")
    @Headers("Content-Type: application/json")
    Call<ReciboEnvia> obtenerPruebas(@Body EnvioEnvia datos);

    @POST("appFiduciaResources/valorar")
    @Headers("Content-Type: application/json")
    Call<ReciboValora> obtenerPruebas(@Body EnvioValora datos);
}
