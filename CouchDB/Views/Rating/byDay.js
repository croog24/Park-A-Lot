function (doc) {
  if (doc.parkingLotId && doc.dayOfWeek)
	  emit([doc.parkingLotId, doc.dayOfWeek]);
}