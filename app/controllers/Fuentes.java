/**
 * 
 */
package controllers;

import java.io.FileInputStream;

import static org.elasticsearch.node.NodeBuilder.*;
import models.Pelicula;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * @author rfanego
 */
public class Fuentes extends Controller {
	public static Result indexExcelPeliculas(){
		try {
			//TODO agregar xls a proyecto y apuntar relativamente
			Workbook wb = new HSSFWorkbook(new FileInputStream("/home/rfanego/Escritorio/peliculas.xls"));
			Sheet sheet = wb.getSheetAt(0);
			
			Node node = nodeBuilder().node();
			Client client = node.client();

			for (Row row : sheet) {
				int rowNum = row.getRowNum();
				if(rowNum >= 9 && rowNum < 30){
					Pelicula pelicula = new Pelicula();
					pelicula.setNombreComercial(row.getCell(0).getStringCellValue());
					pelicula.setNombreOriginal(row.getCell(1).getStringCellValue());
					IndexResponse response = client.prepareIndex("TF", "pelicula")
												   .setSource(Json.toJson(pelicula).asText())
												   .execute().actionGet();
					Logger.debug("Número de row: " + rowNum);
					Logger.debug("Título Comercial: " + row.getCell(0).getStringCellValue());
					Logger.debug("Título Original: " + row.getCell(1).getStringCellValue());
				}
			}
		} catch (Exception e) {
			Logger.error("Processing excel", e);
		}

		return ok();
	}
}
