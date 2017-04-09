function (doc) {
  if (doc.parkingLotId && doc.ratingId)
	  emit(doc.parkingLotId);
}