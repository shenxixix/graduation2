/**
 * 坐标偏移转换
 * 使用高德地图的业务，坐标传入前都要使用此方法转换
 */

 const a = 6378245.0;
 const ee = 0.00669342162296594323;
 
 /**
  * 判断是否在国内，不在国内则不做偏移
  * @param lng
  * @param lat
  * @returns {boolean}
  */
 const outOfChina = (lng, lat) => {
   return (lng < 72.004 || lng > 137.8347) || ((lat < 0.8293 || lat > 55.8271) || false);
 };
 const transformlat = (lng, lat) => {
   let ret = -100.0 + 2.0 * lng + 3.0 * lat + 0.2 * lat * lat + 0.1 * lng * lat + 0.2 * Math.sqrt(Math.abs(lng));
   ret += (20.0 * Math.sin(6.0 * lng * Math.PI) + 20.0 * Math.sin(2.0 * lng * Math.PI)) * 2.0 / 3.0;
   ret += (20.0 * Math.sin(lat * Math.PI) + 40.0 * Math.sin(lat / 3.0 * Math.PI)) * 2.0 / 3.0;
   ret += (160.0 * Math.sin(lat / 12.0 * Math.PI) + 320 * Math.sin(lat * Math.PI / 30.0)) * 2.0 / 3.0;
   return ret;
 };
 
 const transformlng = (lng, lat) => {
   let ret = 300.0 + lng + 2.0 * lat + 0.1 * lng * lng + 0.1 * lng * lat + 0.1 * Math.sqrt(Math.abs(lng));
   ret += (20.0 * Math.sin(6.0 * lng * Math.PI) + 20.0 * Math.sin(2.0 * lng * Math.PI)) * 2.0 / 3.0;
   ret += (20.0 * Math.sin(lng * Math.PI) + 40.0 * Math.sin(lng / 3.0 * Math.PI)) * 2.0 / 3.0;
   ret += (150.0 * Math.sin(lng / 12.0 * Math.PI) + 300.0 * Math.sin(lng / 30.0 * Math.PI)) * 2.0 / 3.0;
   return ret;
 };
 
 const wgs84togcj02M = (lng, lat) => {
   if (outOfChina(lng, lat)) {
     return [lng, lat];
   }
   let dlat = transformlat(lng - 105.0, lat - 35.0);
   let dlng = transformlng(lng - 105.0, lat - 35.0);
   let radlat = lat / 180.0 * Math.PI;
   let magic = Math.sin(radlat);
   magic = 1 - ee * magic * magic;
   let sqrtmagic = Math.sqrt(magic);
   dlat = (dlat * 180.0) / ((a * (1 - ee)) / (magic * sqrtmagic) * Math.PI);
   dlng = (dlng * 180.0) / (a / sqrtmagic * Math.cos(radlat) * Math.PI);
   let mglat = lat + dlat;
   let mglng = lng + dlng;
   return [mglng, mglat];
 };
 
 export default wgs84togcj02M;
 