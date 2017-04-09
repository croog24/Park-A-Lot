function (doc) {
  if (doc.parkingLotId && doc.hour)
	  emit([doc.parkingLotId, doc.hour);
}