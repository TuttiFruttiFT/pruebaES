/**
 * 
 */
package controllers;

import java.io.FileInputStream;

import static org.elasticsearch.node.NodeBuilder.*;
import models.Pelicula;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
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
			
			Node node = nodeBuilder().clusterName("tuttifrutti").client(true).node();
			Client client = node.client();

			for (Row row : sheet) {
				try{
					Pelicula pelicula = new Pelicula();
					Cell rowNombreComercial = row.getCell(0);
					Cell rowNombreOriginal = row.getCell(1);
					if(rowNombreComercial != null){						
						pelicula.setNombreComercial(rowNombreComercial.getStringCellValue());
					}
					if(rowNombreOriginal != null){						
						pelicula.setNombreOriginal(rowNombreOriginal.getStringCellValue());
					}
					
					String json = Json.toJson(pelicula).toString();
					
					Logger.debug("json row: " + json);
					
					IndexResponse response = client.prepareIndex("tf", "pelicula")
							.setSource(json)
							.execute().actionGet();
				}catch(Exception e){
					Logger.error("Error procesando row " + row.getRowNum(),e);
				}
			}
		} catch (Exception e) {
			Logger.error("Processing excel", e);
		}

		return ok();
	}
}
