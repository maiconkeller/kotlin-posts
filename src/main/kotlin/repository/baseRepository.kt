package repository

import entity.FullParameters
import entity.HttpResponse
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

abstract class BaseRepository() {

    fun execute(fullParameters: FullParameters): HttpResponse {

        val conn: HttpURLConnection
        val url: URL = URL(fullParameters.url + getQuery(fullParameters.parameters))
        val response: HttpResponse

        conn = url.openConnection() as HttpURLConnection
        conn.readTimeout = 1000
        conn.connectTimeout = 120000
        conn.requestMethod = fullParameters.method.toString()

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencode")
        conn.setRequestProperty("charset", "utf-8")

        conn.useCaches = false

        // Faz requisição
        conn.connect()

        if (conn.responseCode == 404) {
            response = HttpResponse(conn.responseCode, "")
        } else {
            val inputStream: InputStream = conn.inputStream
            response = HttpResponse(conn.responseCode,  getStringFromInputString(inputStream))
        }

        return response

    }

    private fun getStringFromInputString(inputStream: InputStream): String {

        val strBuilder: StringBuilder = StringBuilder()

        try {

            val br: BufferedReader = BufferedReader(InputStreamReader(inputStream))

            for (line in br.lines()) {
                strBuilder.append(line)
            }

        } catch (e: Exception) {
            return ""
        }

        return strBuilder.toString()
    }

    private fun getQuery(parameters: Map<String, String>): String {

        if (parameters.isEmpty())
            return ""

        val result: StringBuilder = StringBuilder()
        var first: Boolean = true

        for (param in parameters) {
            if (first) {
                result.append("?")
                first = false
            } else {
                result.append("&")
            }

            result.append(URLEncoder.encode(param.key), "UTF-8")
            result.append("=")
            result.append(URLEncoder.encode(param.value), "UTF-8")
        }

        return result.toString()
    }

}